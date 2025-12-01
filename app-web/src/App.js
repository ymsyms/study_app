import './App.css';
import { useEffect, useState } from 'react';
import axios from 'axios';

function App() {
  const [users,setUsers] = useState([]);
  const [dataIsLoaded,setDataIsLoaded] = useState(false);

  function fetchUsers() {
    axios
    .get("http://localhost:8080/users/list")
    .then(function (res){
      setUsers(res.data);
      setDataIsLoaded(true);
    })
    .catch(function(error){
      console.error("Error fetching users:", error);
    });//then
  }//fun

  useEffect(function(){
    fetchUsers();
  }, []);

  return (
    <div className="App">
      <h1>Fetch data from Spring controller</h1>
      <div>
        <ol>
          {users.map((user,index)=> (
            <li key={index}>{user}</li>
          ))}
        </ol>
      </div>
    </div>
  );
}

export default App;
