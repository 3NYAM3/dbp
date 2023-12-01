const LoginPage = () => {
    return (
        <div className="create-container">
            <div>
                <input placeholder="아이디"/>
                <input placeholder="비밀번호"/>
                <input type="submit"
                       onClick={()=>{
                           //클릭 구현
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
                       onClick={()=>{
                            //클릭 구현
                       }}
                />
            </div>
        </div>
    );
};
export default LoginPage