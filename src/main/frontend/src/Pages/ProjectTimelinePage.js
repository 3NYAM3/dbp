import React, {useEffect, useState} from 'react';
import axios from "axios";
import {setTaskNum} from "../store/numSlice";
import {useDispatch} from "react-redux";
import {useNavigate} from "react-router-dom";

const ProjectTimelinePage = () => {
    const [projectName, setProjectName] = useState('');
    const [projectStart, setProjectStart] = useState('');
    const [projectEnd, setProjectEnd] = useState('');
    const [taskList, setTaskList] = useState([]);
    const dispatch = useDispatch();
    const navigate = useNavigate();

    useEffect(() => {
        axios.get(`/api/project/${localStorage.getItem('projectNum')}`).then((res) => {
            setProjectName(res.data.data.projectName);
            setProjectStart(res.data.data.startDate);
            setProjectEnd(res.data.data.lastDate);
        }).catch(e => {
            console.log('대시보드 정보 가져오지 못함');
        });

        axios.get(`/api/project/task/list/${localStorage.getItem('projectNum')}`).then((res) => {
            setTaskList(res.data.data);
        }).catch(e => {
            console.log('작업 리스트 가져오지 못함')
        })
    }, []);

    const oneDayTo10px = (input) => { // 1일당 10px
        return (
            (new Date(input) / (1000 * 60 * 60 * 24)) * 10
        )
    }

    const ps = oneDayTo10px(projectStart); // 프로젝트 시작
    const pe = oneDayTo10px(projectEnd); // 프로젝트 끝
    const pl = pe - ps; // 프로젝트 길이


    return (
        <>
            <div className="project-search">
                <h2>{projectName}</h2>
                <p>{projectStart} ~ {projectEnd}</p>
            </div>
            <div className="container-timeline">
                {taskList.map((task, index) => {

                    const ts = oneDayTo10px(task.startDate) - ps; // 작업 시작
                    const tl = oneDayTo10px(task.lastDate) - oneDayTo10px(task.startDate); // 작업 길이

                    return (
                        <div
                            className="container-timeline-div"
                            key={index}
                            style={{
                                width: `${pl}px`,
                            }}
                        >
                            <div
                                className="container-timeline-div-div"
                                style={{
                                    width: `${tl}px`,
                                    left: `${ts}px`,
                                }}
                                onClick={() => {
                                    dispatch(setTaskNum(task.taskId));
                                    navigate('/project/task/edit');
                                }}
                            >
                                {task.content}
                            </div>
                        </div>
                    );
                })}
            </div>
        </>
    );
}

export default ProjectTimelinePage;
