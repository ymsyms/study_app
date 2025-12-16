import { useState, useEffect } from 'react';
import axios from 'axios';

export default function Users() {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    axios.get("http://localhost:8080/users/list")
      .then(res => {
        setUsers(res.data);
        setLoading(false);
      })
      .catch(error => {
        if (error.response && error.response.status === 403) {
          setError("You are not authorized to view users.");
        } else {
          setError("Error fetching users.");
        }
        setLoading(false);
      });
  }, []);

  return (
    <div style={{ padding: '20px' }}>
      <h2>Users List</h2>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      {loading ? (
        <p>Loading...</p>
      ) : (
        <ol>
          {users.map((user, index) => (
            <li key={index}>{user}</li>
          ))}
        </ol>
      )}
    </div>
  );
}
