import {Link, NavLink, useLocation} from "react-router-dom";
import {useState} from "react";

const NavBar = () => {
    const location = useLocation();
    const [showBox, setShowBow] = useState(false);

    return (
        <nav>
            <div className="nav-container">
                <Link className="nav-logo" to="/">Pp</Link>
                {location.pathname.startsWith('/project/') && <ul>
                    <li>
                        <NavLink
                            className={({isActive}) => "link" + (isActive ? " activate" : "")}
                            aria-current="page"
                            to="/project/summary"
                        >
                            개요
                        </NavLink>
                    </li>
                    <li>
                        <NavLink
                            className={({isActive}) => "link" + (isActive ? " activate" : "")}
                            aria-current="page"
                            to="/project/task"
                        >
                            작업
                        </NavLink>
                    </li>
                    <li>
                        <NavLink
                            className={({isActive}) => "link" + (isActive ? " activate" : "")}
                            aria-current="page"
                            to="/project/timeline"
                        >
                            타임라인
                        </NavLink>
                    </li>
                    <li>
                        <NavLink
                            className={({isActive}) => "link" + (isActive ? " activate" : "")}
                            aria-current="page"
                            to="/project/management"
                        >
                            관리
                        </NavLink>
                    </li>
                </ul>}
                {/*{true && <Link className="navbar-brand" to="/">Me</Link>}*/}
                <div
                    style={{
                        fontSize: 20,
                        cursor:"pointer"
                    }}
                    onClick={()=>{setShowBow(!showBox)}}
                >
                    ...
                </div>
            </div>
            {showBox && <div className="nav-box">
                <div className="nav-box-container">
                    <div>사용자</div>
                    <hr/>
                    <div>로그아웃</div>
                </div>
            </div>}
        </nav>
    )
}

export default NavBar;