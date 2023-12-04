import {useState} from "react";
import {useDispatch} from "react-redux";
import {login} from "../store/authSlice";
import {useNavigate} from "react-router-dom";
import axios from "axios";

const LoginPage = () => {
    const [loginEmail, setLoginEmail] = useState('');
    const [loginPassword, setLoginPassword] = useState('');

    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [passwordCheck, setPasswordCheck] = useState('');

    const dispatch = useDispatch();
    const navigate = useNavigate();

    const [loginCheck, setLoginCheck] = useState(false);
    const [signupEmailCheck, setSignupEmailCheck] = useState(false);
    const [signupPasswordCheck, setSignupPasswordCheck] = useState(false);

    // 로그인 submit
    const handleLoginSubmit = async (e) => {
        e.preventDefault(); // onSubmit 기본동작(새로고침 등) 방지
        const res = await axios.post('/api/members/login', {loginEmail, loginPassword}); // axios 수정 필요
        if (res) { // 로그인 성공 // res 수정 필요(?)
            dispatch(login());
            navigate('/');
        } else { // 로그인 실패
            setLoginCheck(true); // 아이디 비번 잘못된 입력 p태그 보여줌
        }
    };

    // 회원가입 submit
    const handleSignupSubmit = async (e) => {
        e.preventDefault(); // onSubmit 기본동작(새로고침 등) 방지
        if (password === passwordCheck) { // 비밀번호
            try {
                const res = await axios.post('/api/members/signUp', {name, email, password}); // axios 수정 필요
                if (res.status===200&&res.data.result) { // 회원가입 성공 // res 수정 필요(?)
                    dispatch(login());
                    navigate('/login');
                    window.location.reload();
                } else { // 회원가입 실패
                    setSignupEmailCheck(true); // 이메일 중복 p태그 보여줌
                }
            }catch (error){
                console.error("Error", error);
                setSignupEmailCheck(true); // 이메일 중복 p태그 보여줌
            }

        } else {
            setSignupPasswordCheck(true); // 비번 불일치 p태그 보여줌
        }
    };


    return (
        <div className="login-box">
            <div className="login-container" id="login-container">
                <div className="form-container sign-in">
                    <form onSubmit={handleLoginSubmit}>
                        <h1>로그인</h1>
                        <input type="email" placeholder="이메일" required={true}
                               value={loginEmail} onChange={(e) => setLoginEmail(e.target.value)}/>
                        <input type="password" placeholder="비밀번호" required={true}
                               value={loginPassword} onChange={(e) => setLoginPassword(e.target.value)}/>
                        {loginCheck && <p style={{color: "red"}}
                        >이메일 또는 비밀번호를 잘못 입력했습니다.<br/>입력하신 내용을 다시 확인해주세요.</p>}
                        <button type="submit">로그인</button>
                    </form>
                </div>
                <div className="form-container sign-up">
                    <form onSubmit={handleSignupSubmit}>
                        <h1>회원가입</h1>
                        <input type="text" placeholder="이름" required={true}
                               value={name} onChange={(e) => setName(e.target.value)}/>
                        <input type="email" placeholder="이메일" required={true}
                               style={{border: signupEmailCheck && "1px solid red"}}
                               value={email} onChange={(e) => setEmail(e.target.value)}/>
                        {signupEmailCheck && <p style={{color: "red"}}>이메일 중복</p>}
                        <input type="password" placeholder="비밀번호" required={true}
                               style={{border: signupPasswordCheck && "1px solid red"}}
                               value={password} onChange={(e) => setPassword(e.target.value)}/>
                        <input type="password" placeholder="비밀번호 확인" required={true}
                               style={{border: signupPasswordCheck && "1px solid red"}}
                               value={passwordCheck} onChange={(e) => setPasswordCheck(e.target.value)}/>
                        {signupPasswordCheck && <p style={{color: "red"}}>비밀번호가 일치하지 않습니다.</p>}
                        <button type="submit">회원가입</button>
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