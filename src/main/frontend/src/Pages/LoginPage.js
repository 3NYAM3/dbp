import {useState} from "react";

const LoginPage = () => {
    const [loginId, setLoginId] = useState();
    const [loginPassword, setLoginPassword] = useState();

    const [name, setName] = useState();
    const [frontNumber, setFrontNumber] = useState();
    const [endNumber, setEndNumber] = useState();
    const [id, setId] = useState();
    const [password, setPassword] = useState();
    const [passwordCheck, setPasswordCheck] = useState();
    const [email, setEmail] = useState();
    const [phoneNumber, setPhoneNumber] = useState();


    return (
        <div className="login-box">
            <div className="login-container" id="login-container">
                <div className="form-container sign-in">
                    <form>
                        <h1>로그인</h1>
                        <input type="text" placeholder="아이디" required={true}
                               value={loginId} onChange={(e) => setLoginId(e.target.value)}/>
                        <input type="password" placeholder="비밀번호" required={true}
                               value={loginPassword} onChange={(e) => setLoginPassword(e.target.value)}/>
                        <button
                            onClick={()=>{
                                // 로그인 로직
                            }}
                        >로그인</button>
                    </form>
                </div>
                <div className="form-container sign-up">
                    <form>
                        <h1>회원가입</h1>
                        <input type="text" placeholder="이름" required={true}
                               value={name} onChange={(e) => setName(e.target.value)}/>
                        <input type="number" placeholder="주민번호 앞 6자리" required={true}
                               value={frontNumber} onChange={(e) => setFrontNumber(e.target.value)}/>
                        <input type="number" placeholder="주민번호 뒷 1자리" required={true}
                               value={endNumber} onChange={(e) => setEndNumber(e.target.value)}/>
                        <input type="text" placeholder="아이디" required={true}
                               value={id} onChange={(e) => setId(e.target.value)}/>
                        <input type="password" placeholder="비밀번호" required={true}
                               value={password} onChange={(e) => setPassword(e.target.value)}/>
                        <input type="password" placeholder="비밀번호 확인" required={true}
                               value={passwordCheck} onChange={(e) => setPasswordCheck(e.target.value)}/>
                        <input type="email" placeholder="이메일" required={true}
                               value={email} onChange={(e) => setEmail(e.target.value)}/>
                        <input type="number" placeholder="전화번호" required={true}
                               value={phoneNumber} onChange={(e) => setPhoneNumber(e.target.value)}/>
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