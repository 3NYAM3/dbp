import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import propTypes from "prop-types";
import axios from "axios";
import {useSelector} from "react-redux";

const ProjectForm = ({editing}) => {
    const navigate = useNavigate();
    const [title, setTitle] = useState('');
    const [type, setType] = useState('');
    const [start, setStart] = useState('');
    const [end, setEnd] = useState('');
    const [memberList, setMemberList] = useState([]);
    const [email, setEmail] = useState('');
    const num = useSelector(state => state.num.projectNum);
    const [isAdmin, setIsAdmin] = useState(false);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        if (editing) {
            axios.get(`/api/project/pm/${num}`, {headers: {'Authorization': `Bearer ${localStorage.getItem('isLoggedIn')}`}})
                .then((res) => {
                    setIsAdmin(res.data.result);
                    setLoading(false);
                })
                .catch(e => {
                    console.log('유저 정보 가져오지 못함');
                });
        }
        // 수정 페이지 일 때 받아와야함
        // console.log(num);
        // axios.get().then((res) => {
        //     setTitle(res.title);
        //     setType(res.type);
        //     setStart(res.start);
        //     setEnd(res.end);
        //     setMemberList(res.data);
        // }).catch(e => {
        //     console.log('수정페이지 받아오지 못함');
        // })
    }, []);


    const handleSubmit = (e) => {
        e.preventDefault();
        if (editing) { // 프로젝트 수정 페이지 submit
            // 수정된 회원 목록 전송해 줘야함
            // axios.patch('', {title, type, start, end}).then(() => {
            //     navigate('/project/dashboard')
            // })
            navigate('project/dashboard');
        } else { // 프로젝트 생성 페이지 submit
            axios.post('/api/project/create',
                {
                    projectName: title,
                    type: type,
                    startDate: start,
                    lastDate: end,
                    memberList: memberList
                },
                {headers: {'Authorization': `Bearer ${localStorage.getItem('isLoggedIn')}`}}).then((res) => {
                navigate('/project')
            }).catch(e => {
                console.log('프로젝트 생성 실패')
            })
        }
    }




    if (editing && loading){
        return <></>
    }

    if (isAdmin||!editing) {
        return (
            <form onSubmit={handleSubmit}>
                <div className="container-common">
                    <div>
                        <h1>프로젝트 {editing ? '수정' : '생성'}</h1><br/>
                        <label>프로젝트명</label>
                        <input type="text" placeholder="프로젝트명" value={title} required
                               onChange={(e) => setTitle(e.target.value)}/>
                        <br/><br/><br/>
                        <label>타입</label>
                        <input type="text" placeholder="타입" value={type} required
                               onChange={(e) => setType(e.target.value)}/>
                        <br/><br/><br/>
                        <label>시작일</label>
                        <input type="date" placeholder="시작일" value={start} required
                               onChange={(e) => setStart(e.target.value)}/>
                        <br/><br/><br/>
                        <label>마감일</label>
                        <input type="date" placeholder="마감일" value={end} required
                               onChange={(e) => setEnd(e.target.value)}/>
                        <br/><br/><br/>

                        <div style={{
                            width: '100%',
                            backgroundColor: "#091E4224",
                            borderRadius: "10px"
                        }}>
                            <h3 style={{textAlign: "center"}}>회원명단</h3>

                            {memberList.map((member) => (
                                <div key={member} style={{ // 수정 페이지 일 때 회원명단 표시
                                    width: '100%',
                                    height: '30px',
                                    display: 'flex',
                                    justifyContent: "space-between"
                                }}
                                >
                                    <p style={{
                                        margin: "0px",
                                        textAlign: "center",
                                        width: "100%",
                                        height: "30px"
                                    }}>{member}</p>
                                    <button
                                        style={{
                                            boxSizing: "border-box",
                                            width: "27px",
                                            height: "27px",
                                            margin: "1.5px",
                                            backgroundColor: "#EF4040CC",
                                            color: "#FFFFFF",
                                            border: "none"
                                        }}
                                        onClick={() => {
                                            setMemberList(memberList.filter(memberList => memberList !== member));
                                        }}
                                    >
                                        X
                                    </button>
                                </div>
                            ))}

                            <div style={{
                                width: '100%',
                                display: 'flex',
                                justifyContent: "space-between"
                            }}>
                                <input
                                    id='inputEmail'
                                    placeholder="추가할 회원 이메일 입력"
                                    value={email}
                                    onChange={(e) => setEmail(e.target.value)}
                                />
                                <button
                                    style={{
                                        width: "50px",
                                        color: "#FFFFFF",
                                        backgroundColor: "#7C93C3"
                                    }}
                                    type="button"
                                    onClick={() => {

                                        const inputEmail = document.getElementById('inputEmail');

                                        // 이미 회원명단에 추가된 경우
                                        if (email !== '') {
                                            if (memberList.includes(email)) {
                                                setEmail('');
                                                inputEmail.style.border = '2px solid green';
                                                inputEmail.placeholder = '이미 추가된 회원 입니다.';
                                            } else { // 회원명단에 없는 경우
                                                // 추가할 회원의 이메일 확인
                                                axios.get(`/api/project/create/${email}`).then((res) => {
                                                    if (res.data.result) { // db에 이메일이 있을 경우
                                                        setMemberList([...memberList, email]);
                                                        setEmail('');
                                                        inputEmail.style.border = '1px solid black';
                                                        inputEmail.placeholder = '추가할 회원 이메일 입력';
                                                    } else { // db에 이메일이 없을 경우
                                                        setEmail('');
                                                        inputEmail.style.border = '1px solid red';
                                                        inputEmail.placeholder = '정확한 이메일을 입력해 주세요';
                                                    }
                                                }).catch(e => { // 못가져 왔을 경우 예외처리
                                                    console.log('유저 정보 요청 실패')
                                                })


                                            }
                                        } else {
                                            inputEmail.style.border = '2px solid blue';
                                        }


                                    }}
                                >추가
                                </button>
                            </div>
                        </div>

                        <br/><br/>
                        <button
                            type="submit"
                            className="ok-common"
                        >
                            {editing ? '수정' : '생성'}
                        </button>
                        <br/><br/>
                        <input
                            className="cancel-common"
                            type="button"
                            value="취소"
                            onClick={() => editing ? navigate('/project/dashboard') : navigate('/project')}
                        />
                        <br/><br/>
                        {editing && <input
                            className="del-common"
                            type="button"
                            value="삭제"
                            onClick={() => {
                                // todo 삭제 로직 구현
                                editing ? navigate('/project/dashboard') : navigate('/project')
                            }}
                        />}
                    </div>
                </div>
            </form>
        )

    } else {
        return (
            <div className="container-common">
                <div>
                    <h1>프로젝트 나가기</h1>
                    <button className="del-common">탈퇴</button>
                </div>

            </div>
        )
    }


}

ProjectForm.propType = {
    editing: propTypes.bool
}
ProjectForm.defaultProps = {
    editing: false
}

export default ProjectForm;