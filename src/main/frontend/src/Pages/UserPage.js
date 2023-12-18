import {useEffect, useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";

const UserPage = () => {
    const [showChange, setShowChange] = useState(false);

    const [nowPassword, setNowPassword] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [newPasswordCheck, setNewPasswordCheck] = useState('');

    const [name, setName] = useState('');
    const [email, setEmail] = useState('');

    const navigate = useNavigate();


    const handleSubmit = async (e) => {
        e.preventDefault();
        if (newPassword !== newPasswordCheck) {
            alert('새 비밀번호와 비밀번호 확인이 일치하지 않습니다.');
        } else { // 비밀번호 변경 요청
            axios.put('/api/members/info',
                {nowPassword: nowPassword, changePassword: newPassword},
                {headers: {'Authorization': `Bearer ${localStorage.getItem('isLoggedIn')}`}},
            ).then((res) => {
                if (res.data.result) { // 비밀번호 변경 성공
                    //toast 띄워볼까
                    setShowChange(false);
                    setNowPassword('');
                    setNewPassword('');
                    setNewPasswordCheck('');
                } else {
                    // 현재 비밀번호가 일치하지 않습니다.
                }
            }).catch(e => {
                console.log('비밀번호 변경 못함');
            })
        }
    }

    useEffect(() => {
        axios.get('/api/members/info', {headers: {'Authorization': `Bearer ${localStorage.getItem('isLoggedIn')}`}}).then((res) => { // get으로 가져옴
            setName(res.data.data.name);
            setEmail(res.data.data.email);
        }).catch(e => { // 못가져 왔을 경우 예외처리
            console.log('유저 정보 가져오지 못함')
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
                <br/><br/>
                <button className="del-common"
                        onClick={() => { // 회원 탈퇴 todo
                            axios.delete('/api/members/withdrawal', {headers: {'Authorization': `Bearer ${localStorage.getItem('isLoggedIn')}`}}).then((res) => {
                                console.log(res);
                            }).catch(e => {
                                console.log('회원 탈퇴 못함');
                            })
                        }}
                >회원 탈퇴
                </button>
            </div>
        </div>
    )
}

export default UserPage;