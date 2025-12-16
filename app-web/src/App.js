import './App.css';
import { BrowserRouter as Router, Routes, Route, Link, Navigate } from 'react-router-dom';
import Topics from './components/Topics';
import Users from './components/Users';
import Login from './components/Login';
import { AuthProvider, useAuth } from './context/AuthContext';

function PrivateRoute({ children }) {
  const { user } = useAuth();
  return user ? children : <Navigate to="/login" />;
}

function RoleRoute({ children, allowed }) {
  const { user } = useAuth();
  if (!user) return <Navigate to="/login" />;
  return allowed.includes(user.role) ? children : <Navigate to="/" />;
}

function Sidebar() {
  const { user, logout } = useAuth();
  
  if (!user) return null;

  return (
    <nav className="sidebar">
      <h2>Study App</h2>
      <div style={{ padding: '0 10px 10px', fontSize: '0.9em', color: '#aaa' }}>
        Logged in as: <strong style={{ color: 'white' }}>{user.username}</strong> ({user.role})
      </div>
      <Link to="/topics" className="nav-link">Topics</Link>
      {user.role === 'ADMIN' && <Link to="/users" className="nav-link">Users</Link>}
      <button 
        onClick={logout} 
        className="nav-link" 
        style={{ background: 'none', border: 'none', textAlign: 'left', cursor: 'pointer', fontSize: '1rem', fontFamily: 'inherit' }}
      >
        Logout
      </button>
    </nav>
  );
}

function AppContent() {
  const { user } = useAuth();
  
  return (
    <div className="app-container">
      <Sidebar />
      
      <main className="content">
        <Routes>
          <Route path="/login" element={!user ? <Login /> : <Navigate to="/" />} />
          <Route path="/topics" element={<PrivateRoute><Topics /></PrivateRoute>} />
          <Route path="/users" element={<RoleRoute allowed={['ADMIN']}><Users /></RoleRoute>} />
          <Route path="/" element={
            <PrivateRoute>
              <div style={{textAlign: 'center', marginTop: '50px'}}>
                <h2>Welcome! Select a tab from the sidebar.</h2>
              </div>
            </PrivateRoute>
          } />
        </Routes>
      </main>
    </div>
  );
}

function App() {
  return (
    <AuthProvider>
      <Router>
        <AppContent />
      </Router>
    </AuthProvider>
  );
}

export default App;
