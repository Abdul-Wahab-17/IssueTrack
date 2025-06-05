import {BrowserRouter as Router , Routes , Route} from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import Home from './pages/Home';
import Navbar from './components/Navbar';
import Dashboard from './pages/Dashboard';

import UserProvider from "./contexts/UserContext";
import LogoutPage from './pages/LogoutPage';

function App() {
  return (
    <UserProvider>
      <Router>
        <Navbar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path='/logout' element={<LogoutPage />} />
        </Routes>
      </Router>
    </UserProvider>
  );
}

export default App;