import {BrowserRouter as Router , Routes , Route} from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import Home from './pages/Home';
import Navbar from './components/Navbar';

function App() {

  return (
    <>

  <Router>
     <Navbar />
    <Routes>
      <Route path='/' element={<Home />} />
      <Route path="/login" element={<LoginPage />} />
    </Routes>
  </Router>
    </>
  )
}

export default App
