import { Link} from 'react-router-dom'
import Home from '../pages/Home';
import LoginPage from '../pages/LoginPage';

function Navbar(){
    return (
        <nav>
            <Link to="/">Home</Link>
            <Link to="/login">Login</Link>
            <Link to="/dashboard">Dashboard</Link>
        </nav>
    )
}

export default Navbar;