import './App.css';
import {
    BrowserRouter,
    Routes,
    Route
} from 'react-router-dom';
import NavBar from "./elements/NavBar";
import routes from "./routes";

function App() {
    return (
        <BrowserRouter>
            <NavBar/>
            <div className="container">
                <Routes>
                    {routes.map((route) => {
                        return <Route
                            key={route.path}
                            path={route.path}
                            element={route.element}
                        />
                    })}
                </Routes>
            </div>
        </BrowserRouter>
    );
}

export default App;