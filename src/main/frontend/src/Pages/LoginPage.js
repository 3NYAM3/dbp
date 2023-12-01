import {useState} from "react";
// import axios from "axios";

const LoginPage = () => {
    let [id, setId] = useState();
    let [password, setPassword] = useState();

    return (
        <div className="create-container">
            <div>
                <input placeholder="아이디"
                       value={id}
                       onChange={(e) => setId(e.target.value)}
                />
                <input placeholder="비번"
                       type="password"
                       value={password}
                       onChange={(e) => setPassword(e.target.value)}
                />
                <input type="submit"
                       onClick={() => {
                           //클릭 로직 구현

                           // console('test')

                           // axios.patch(`http://localhost:5000/posts`, {
                           //     id: id,
                           //     password: password,
                           // }).then(res => { // 완료시
                           //     navigate('/') // 홈페이지로
                           // }).catch(e => { // 실패시
                           //
                           // })
                       }}
                />
            </div>
            <div>
                <input placeholder="이름"/>
                <input placeholder="주민앞6자리"/>
                <input placeholder="주민뒤1자리"/>
                <input placeholder="아이디"/>
                <input placeholder="비밀번호"/>
                <input placeholder="비밀번호 확인"/>
                <input placeholder="이메일"/>
                <input placeholder="전화번호"/>
                <input type="submit"
                       onClick={() => {
                           //클릭 구현
                       }}
                />
            </div>
        </div>
    );
};
export default LoginPage