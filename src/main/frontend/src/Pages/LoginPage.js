import {useState} from "react";
import {useDispatch} from "react-redux";
import {login} from "../store/authSlice";
import {useNavigate} from "react-router-dom";

const LoginPage = () => {
    const [loginEmail, setLoginEmail] = useState();
    const [loginPassword, setLoginPassword] = useState();

    const [name, setName] = useState();
    const [email, setEmail] = useState();
    const [password, setPassword] = useState();
    const [passwordCheck, setPasswordCheck] = useState();

    const dispatch = useDispatch();
    const navigate = useNavigate();

    return (
        <div className="login-box">
            <div className="login-container" id="login-container">
                <div className="form-container sign-in">
                    <form>
                        <h1>로그인</h1>
                        <input type="email" placeholder="이메일" required={true}
                               value={loginEmail} onChange={(e) => setLoginEmail(e.target.value)}/>
                        <input type="password" placeholder="비밀번호" required={true}
                               value={loginPassword} onChange={(e) => setLoginPassword(e.target.value)}/>
                        <button
                            onClick={()=>{
                                // 로그인 로직
                                dispatch(login());
                                navigate('/');
                            }}
                        >로그인</button>
                    </form>
                </div>
                <div className="form-container sign-up">
                    <form>
                        <h1>회원가입</h1>
                        <input type="text" placeholder="이름" required={true}
                               value={name} onChange={(e) => setName(e.target.value)}/>
                        <input type="email" placeholder="이메일" required={true}
                               value={email} onChange={(e) => setEmail(e.target.value)}/>
                        <input type="password" placeholder="비밀번호" required={true}
                               value={password} onChange={(e) => setPassword(e.target.value)}/>
                        <input type="password" placeholder="비밀번호 확인" required={true}
                               value={passwordCheck} onChange={(e) => setPasswordCheck(e.target.value)}/>
                        <button
                            onClick={()=>{
                                // 회원가입 로직
                            }}
                        >회원가입</button>
                    </form>
                </div>
                <div className="toggle-container">
                    <div className="toggle">
                        <div className="toggle-panel toggle-left">
                            <h1>Project Pulse</h1>
                            <p>프로젝트를 관리해 보세요</p>
                            <button
                                onClick={() => {
                                    document.getElementById('login-container').classList.remove("active");
                                }}
                                className="hidden"
                            >
                                로그인
                            </button>
                        </div>
                        <div className="toggle-panel toggle-right">
                            <h1>Project Pulse</h1>
                            <p>계정을 생성해 무료로 사용</p>
                            <button
                                onClick={() => {
                                    console.log("test")
                                    document.getElementById('login-container').classList.add("active");
                                }}
                                className="hidden"
                            >
                                회원가입
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};
export default LoginPage