import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import propTypes from "prop-types";
import axios from "axios";

const ProjectForm = ({editing}) => {
    const navigate = useNavigate();
    const [title, setTitle] = useState('');
    const [type, setType] = useState('');
    const [start, setStart] = useState('');
    const [end, setEnd] = useState('');
    const [memberList, setMamberList] = useState([]);

    useEffect(() => {
        //수정 페이지 일 때 받아와야함
        // axios.get().then((res)=>{
        // setTitle(res.title);
        // setType(res.type);
        // setStart(res.start);
        // setEnd(res.end);
        // })
    }, []);


    const handleSubmit = (e) => {
        e.preventDefault();
        if (editing) { // 프로젝트 수정 페이지 submit
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
    // return (
    //     <div>
    //         <form onSubmit={handleSubmit}>
    //             <div className="create-container">
    //                 <div className="create-container-div">
    //                     <h1>프로젝트 {editing ? '수정' : '생성'}</h1><br/>
    //                     <label>프로젝트명</label>
    //                     <input type="text" placeholder="프로젝트명" value={title} required
    //                            onChange={(e) => setTitle(e.target.value)}/>
    //                     <br/><br/><br/>
    //                     <label>타입</label>
    //                     <input type="text" placeholder="타입" value={type} required
    //                            onChange={(e) => setType(e.target.value)}/>
    //                     <br/><br/><br/>
    //                     <label>시작일</label>
    //                     <input type="date" placeholder="시작일" value={start} required
    //                            onChange={(e) => setStart(e.target.value)}/>
    //                     <br/><br/><br/>
    //                     <label>마감일</label>
    //                     <input type="date" placeholder="마감일" value={end} required
    //                            onChange={(e) => setEnd(e.target.value)}/>
    //                     <br/><br/><br/>
    //
    //                     <div style={{width:'100%',
    //                     backgroundColor:"#091E4224",
    //                     borderRadius:"10px"}}>
    //                         <h3 style={{textAlign:"center"}}>회원명단</h3>
    //                         <h4 style={{textAlign:"center"}}>+</h4>
    //                     </div>
    //
    //                     <br/><br/>
    //                     <button
    //                         type="submit"
    //                     >
    //                         {editing ? '수정' : '생성'}
    //                     </button>
    //                     <br/><br/>
    //                     <input
    //                         className="btn-cancel"
    //                         type="button"
    //                         value="취소"
    //                         onClick={() => editing ? navigate('/project/dashboard') : navigate('/project')}
    //                     />
    //                 </div>
    //             </div>
    //         </form>
    //     </div>
    // )

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
                        {editing && <div style={{ // 수정 페이지 일 때 회원명단 표시
                            width: '100%',
                            display: 'flex',
                            justifyContent: "space-between"
                        }}
                        >
                            <p>test</p>
                            <button style={{width: "50px"}}>제거</button>
                        </div>}
                        <div style={{
                            width: '100%',
                            display: 'flex',
                            justifyContent: "space-between"
                        }}>
                            <input id='test' placeholder="추가할 회원 이메일 입력"/>
                            <button
                                style={{width: "50px"}}
                                type="button"
                                onClick={() => {
                                    // 추가할 회원의 이메일 확인
                                    //axios.get
                                    // document.getElementById('test').style.border = '1px solid red';
                                }}
                            >추가
                            </button>
                        </div>
                        <p>test</p>
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
                </div>
            </div>
        </form>
    )
}

ProjectForm.propType = {
    editing: propTypes.bool
}
ProjectForm.defaultProps = {
    editing: false
}

export default ProjectForm;