import { BrowserRouter, Route, Routes } from 'react-router-dom';

import './App.css';
import HomePage from './pages/HomePage';
import Login from './pages/Login';
import Register from './pages/Register';
import AddProperty from './pages/AddProperty';
import ProfilePage from './pages/ProfilePage';
function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<HomePage />} />
        <Route path='/login' element={<Login />} />
        <Route path='/register' element={<Register />} />
        <Route path='/addProperty' element={<AddProperty />} />
        <Route path='/userProfile' element={<ProfilePage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
