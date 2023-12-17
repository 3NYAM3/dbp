import {memo, useState} from "react";
import propTypes from "prop-types";
import {useNavigate} from "react-router-dom";
import axios from "axios";

const TaskForm = ({editing}) => {
    const [task, setTask] = useState('');
    const [manager, setManager] = useState('');
    const [start, setStart] = useState('');
    const [end, setEnd] = useState('');
    const navigate = useNavigate();

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
                <label>담당자</label>
                <input type="text" value={manager} onChange={(e) => {
                    setManager(e.target.value)
                }}/>
                <br/><br/>
                <label>시작일</label>
                <input type="date" value={start} onChange={(e) => {
                    setStart(e.target.value)
                }}/>
                <br/><br/>
                <label>마감일</label>
                <input type="date" value={end} onChange={(e) => {
                    setEnd(e.target.value)
                }}/>
                <br/><br/><br/>
                <button className="ok-common"
                        onClick={() => {
                            if (editing) {

                            } else {
                                axios.post(`/api/project/task/${localStorage.getItem('projectNum')}/create`, {
                                    content: task,
                                    memo: memo,
                                    startDate: start,
                                    lastDate: end
                                }).then((res) => {
                                    console.log(res);
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