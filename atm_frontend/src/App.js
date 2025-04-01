import logo from './logo.svg';
import './App.css';
import Login from './Components/login';
import { useState } from 'react';
import ATMList from './Components/ATMList';
import { AppBar, Box, Button, Toolbar, Typography } from '@mui/material';

function App() {

  const [isAuthenticationed,setAuth] = useState(false);

  const handleLogout = () => {
    console.log("로그아웃 handle",isAuthenticationed);
    sessionStorage.removeItem("jwt");
    setAuth(false);
  }

  const handleLogin = () => {
    console.log("로그인 handle",isAuthenticationed);
    setAuth(true);
  }

  return (
    <div className="App">
      <Box sx={{flexGrow:1}}/>
        {isAuthenticationed ? 
          (<Button onClick={handleLogout} variant='contained' color='info'>로그아웃</Button>)
          :
          (<></>)
        }
        

      {isAuthenticationed ?
       (<ATMList/>)
       :
       (<Login handleLogin = {handleLogin}/>)
      }
    </div>
  );
}

export default App;
