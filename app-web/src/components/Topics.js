import { useState, useEffect } from 'react';
import axios from 'axios';
import { useAuth } from '../context/AuthContext';

const API_URL = "http://localhost:8080/topics";

export default function Topics() {
    const [topics, setTopics] = useState([]);
    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const [editingId, setEditingId] = useState(null);
    const { user } = useAuth();
    const canEdit = user && (user.role === 'ADMIN' || user.role === 'MODERATOR');

    useEffect(() => {
        fetchTopics();
    }, []);

    const fetchTopics = () => {
        axios.get(API_URL)
            .then(res => setTopics(res.data))
            .catch(err => console.error("Error fetching topics:", err));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        const topicData = { title, description };

        if (editingId) {
            axios.put(`${API_URL}/${editingId}`, topicData)
                .then(() => {
                    fetchTopics();
                    resetForm();
                })
                .catch(err => {
                    if (err.response && err.response.status === 403) {
                        alert("You do not have permission to update topics.");
                    } else {
                        console.error("Error updating topic:", err);
                    }
                });
        } else {
            axios.post(API_URL, topicData)
                .then(() => {
                    fetchTopics();
                    resetForm();
                })
                .catch(err => {
                    if (err.response && err.response.status === 403) {
                        alert("You do not have permission to create topics.");
                    } else {
                        console.error("Error creating topic:", err);
                    }
                });
        }
    };

    const handleEdit = (topic) => {
        setEditingId(topic.id);
        setTitle(topic.title);
        setDescription(topic.description);
    };

    const handleDelete = (id) => {
        if (window.confirm("Are you sure you want to delete this topic?")) {
            axios.delete(`${API_URL}/${id}`)
                .then(() => fetchTopics())
                .catch(err => {
                    if (err.response && err.response.status === 403) {
                        alert("You do not have permission to delete topics.");
                    } else {
                        console.error("Error deleting topic:", err);
                    }
                });
        }
    };

    const resetForm = () => {
        setEditingId(null);
        setTitle("");
        setDescription("");
    };

    return (
        <div style={{ padding: '20px', border: '1px solid #ccc', borderRadius: '8px', marginTop: '20px' }}>
            <h2>Topics Management</h2>
            
            {canEdit && (
              <form onSubmit={handleSubmit} style={{ marginBottom: '20px', display: 'flex', gap: '10px', justifyContent: 'center' }}>
                  <input
                      type="text"
                      placeholder="Title"
                      value={title}
                      onChange={(e) => setTitle(e.target.value)}
                      required
                      style={{ padding: '5px' }}
                  />
                  <input
                      type="text"
                      placeholder="Description"
                      value={description}
                      onChange={(e) => setDescription(e.target.value)}
                      required
                      style={{ padding: '5px', width: '300px' }}
                  />
                  <button type="submit" style={{ padding: '5px 15px', cursor: 'pointer' }}>
                      {editingId ? "Update" : "Add"}
                  </button>
                  {editingId && (
                      <button type="button" onClick={resetForm} style={{ padding: '5px 15px', cursor: 'pointer', backgroundColor: '#f0f0f0' }}>
                          Cancel
                      </button>
                  )}
              </form>
            )}

            <ul style={{ listStyleType: 'none', padding: 0 }}>
                {topics.map(topic => (
                    <li key={topic.id} style={{ marginBottom: '10px', padding: '10px', backgroundColor: '#f9f9f9', borderRadius: '4px', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                        <div style={{ textAlign: 'left' }}>
                            <strong>{topic.title}</strong>: {topic.description}
                        </div>
                        <div>
                            {canEdit && (
                              <>
                                <button onClick={() => handleEdit(topic)} style={{ marginRight: '5px', cursor: 'pointer' }}>Edit</button>
                                <button onClick={() => handleDelete(topic.id)} style={{ cursor: 'pointer', color: 'red' }}>Delete</button>
                              </>
                            )}
                        </div>
                    </li>
                ))}
            </ul>
        </div>
    );
}
