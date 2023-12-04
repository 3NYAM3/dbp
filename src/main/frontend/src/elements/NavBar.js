import {Link, NavLink, useLocation} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {logout} from "../store/authSlice";
import {hide, show} from "../store/boxSlice";

const NavBar = () => {
    const location = useLocation();
    const isLoggedIn = useSelector(state => state.auth.isLoggedIn);
    const showBox = useSelector(state => state.box.showBox);
    const dispatch = useDispatch();
    // const [render, setRender] = useState(true);
    return (
        <nav>
            <div className="nav-container" onClick={() => {
                dispatch(hide());
            }}>
                <Link className="nav-logo" to="/project">Pp</Link>
                {(location.pathname.startsWith('/project/summary') ||location.pathname.startsWith('/project/task')||location.pathname.startsWith('/project/management')||location.pathname.startsWith('/project/timeline'))&& <ul>
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
                {isLoggedIn && <div
                    style={{
                        fontSize: 20,
                        cursor: "pointer"
                    }}
                    onClick={(e) => {
                        e.stopPropagation();
                        if (showBox) {
                            dispatch(hide())
                        } else {
                            dispatch(show())
                        }
                    }}
                >
                    ...
                </div>}
            </div>
            {showBox && <div className="nav-box">
                <div className="nav-box-container">
                    <div
                        onClick={() => {
                            //사용자 페이지로 이동
                            dispatch(hide())
                        }}
                    >
                        사용자
                    </div>
                    <hr/>
                    <div
                        onClick={() => {
                            dispatch(logout());
                            dispatch(hide());
                        }}
                    >
                        로그아웃
                    </div>
                </div>
            </div>}
        </nav>
    )
}

export default NavBar;