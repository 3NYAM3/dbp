import {useEffect, useState} from "react";
import axios from "axios";

const UserPage = () => {
    const [showChange, setShowChange] = useState(false);

    const [nowPassword, setNowPassword] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [newPasswordCheck, setNewPasswordCheck] = useState('');

    const [name, setName] = useState('');
    const [email, setEmail] = useState('');


    const handleSubmit = async (e) => {
        e.preventDefault();
        if (newPassword !== newPasswordCheck) {
            alert('새 비밀번호와 비밀번호 확인이 일치하지 않습니다.');
        } else {
            //axios 현재 비밀번호 일치 확인
        }
    }

    useEffect(() => {
        axios.get('api/members/info').then((res) => { // get으로 가져옴
            setName(res.data.name);
            setEmail(res.data.email);
        }).catch(e=>{ // 못가져 왔을 경우 예외처리

        })
    }, []);

    const show = () => {
        if (!showChange) {
            return (
                <button
                    className="cancel-common"
                    onClick={() => {
                        setShowChange(true);
                    }}
                >
                    비밀번호 변경하기
                </button>
            )
        } else {
            return (
                <form onSubmit={handleSubmit}>
                    <input type="password"
                           className="container-common-input"
                           placeholder="현재 비밀번호"
                           required
                           value={nowPassword}
                           onChange={(e) => setNowPassword(e.target.value)}/>
                    <br/><br/>
                    <input type="password"
                           className="container-common-input"
                           placeholder="새 비밀번호"
                           required
                           value={newPassword}
                           onChange={(e) => setNewPassword(e.target.value)}/>
                    <input type="password"
                           className="container-common-input"
                           placeholder="새 비밀번호 확인"
                           required
                           value={newPasswordCheck}
                           onChange={(e) => setNewPasswordCheck(e.target.value)}/>
                    <br/><br/>
                    <button className="ok-common" type="submit">확인</button>
                    <br/><br/>
                    <button className="cancel-common" onClick={() => {
                        setShowChange(false);
                        setNowPassword('');
                        setNewPassword('');
                        setNewPasswordCheck('');
                    }}>취소
                    </button>
                </form>
            )
        }
    }

    return (
        <div className="container-common">
            <div>
                <h1>{name}</h1>
                <h3>{email}</h3>
                {show()}
            </div>
        </div>
    )
}

export default UserPage;