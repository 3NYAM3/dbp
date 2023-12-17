import {useEffect, useState} from "react";
import propTypes from "prop-types";
import {useNavigate} from "react-router-dom";
import axios from "axios";

const TaskForm = ({editing}) => {
    const [task, setTask] = useState('');
    const [memo, setMemo] = useState('');
    const [start, setStart] = useState('');
    const [end, setEnd] = useState('');
    const navigate = useNavigate();

    const [projectStart, setProjectStart] = useState('');
    const [projectEnd, setProjectEnd] = useState('');

    useEffect(() => {
        axios.get(`/api/project/dashboard/${localStorage.getItem('projectNum')}/`).then((res) => {
            setProjectStart(res.data.data.startDate);
            setProjectEnd(res.data.data.lastDate);
        }).catch(e => {
            console.log('프로젝트 시작일 마감일 가져오지 못함');
        });
    }, []);

    if (editing) { // 수정 페이지일 경우에 값을 가져와서 띄워줌

    }

    return (
        <div className="container-common">
            <div>
                <h1>작업 {editing ? '수정' : '등록'}</h1>
                <label>작업명</label>
                <input type="text" value={task} onChange={(e) => {
                    setTask(e.target.value)
                }}/>
                <br/><br/>
                <label>메모</label>
                <input type="text" value={memo} onChange={(e) => {
                    setMemo(e.target.value)
                }}/>
                <br/><br/>
                <label>시작일</label>
                <input type="date" value={start} onChange={(e) => {
                    setStart(e.target.value)
                }} min={projectStart} max={projectEnd}/>
                <br/><br/>
                <label>마감일</label>
                <input type="date" value={end} onChange={(e) => {
                    setEnd(e.target.value)
                }} min={projectStart} max={projectEnd}/>
                <br/><br/><br/>
                <button
                    className="ok-common"
                    onClick={() => {
                        if (editing) {

                        } else {
                            axios.post(`/api/project/task/${localStorage.getItem('projectNum')}/create`, {
                                content: task,
                                memo: memo,
                                startDate: start,
                                lastDate: end
                            }).then((res) => {
                                navigate(-1);
                            }).catch(e => {
                                console.log('작업 등록 오류')
                            })
                        }
                    }}
                >{editing ? '수정' : '등록'}</button>
                <br/><br/>
                <button className="cancel-common" onClick={() => navigate('/project/task')}>취소</button>
            </div>
        </div>
    )
}

TaskForm.propType = {
    editing: propTypes.bool,
}

TaskForm.defaultProps = {
    editing: false,
}

export default TaskForm;