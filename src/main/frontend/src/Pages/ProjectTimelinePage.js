import React, {useEffect, useState} from 'react';
import axios from "axios";

// // test dataSet
// const taskData = [
//     {content: '작업 1', start: '2023-01-01', end: '2023-01-01'},
//     {content: '작업 2', start: '2023-01-02', end: '2023-01-03'},
//     {content: '작업 3', start: '2023-01-03', end: '2023-01-04'},
//     {content: '작업 4', start: '2023-01-05', end: '2023-01-06'},
//     {content: '작업 5', start: '2023-01-01', end: '2023-01-31'},
//     {content: '작업 6', start: '2023-02-01', end: '2023-03-31'},
//     {content: '작업 7', start: '2023-02-01', end: '2023-04-30'},
//     {content: '작업 8', start: '2023-05-01', end: '2023-12-31'},
//     {content: '작업 9', start: '2023-12-01', end: '2023-12-31'},
// ];
// const projectData = {
//     start: '2023-01-01',
//     end: '2023-12-31'
// }


// let line = [];
// for (let i = new Date(projectData.start); i <= new Date(projectData.end); i.setMonth(i.getMonth() + 1)) {
//     line.push(new Date(i));
// }

// const createline = () => {
//     // 프로젝트 시작일부터 종료일 까지 1달 간격 선
//     for (let i = new Date(projectData.start); i <= new Date(projectData.end); i.setMonth(i.getMonth() + 1)) {
//         line.push(new Date(i));
//     }
//     for (let i = 0; i < line.length; i++) { // test code
//         console.log(new Date(line[i]));
//     }
//     return (
//         line.map((line, index) => {
//             return (
//                 <div
//                     key={index}
//                     style={{
//                         left: oneDayTo10px(line) - ps
//                     }}
//                     className="test"
//                 >
//
//                 </div>
//             )
//         })
//     )
// }


const ProjectTimelinePage = () => {
    const [projectName, setProjectName] = useState('');
    const [projectStart, setProjectStart] = useState('');
    const [projectEnd, setProjectEnd] = useState('');
    const [taskList, setTaskList] = useState([]);

    useEffect(() => {
        axios.get(`/api/project/dashboard/${localStorage.getItem('projectNum')}/`).then((res) => {
            setProjectName(res.data.data.projectName);
            setProjectStart(res.data.data.startDate);
            setProjectEnd(res.data.data.lastDate);
        }).catch(e => {
            console.log('대시보드 정보 가져오지 못함');
        });

        axios.get(`/api/project/task/${localStorage.getItem('projectNum')}/`).then((res) => {
            console.log(res)
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
                            {/*{line.map((line, index) => {*/}
                            {/*    return (*/}
                            {/*        <div*/}
                            {/*            key={index}*/}
                            {/*            style={{*/}
                            {/*                left: oneDayTo10px(line) - ps*/}
                            {/*            }}*/}
                            {/*            className="test"*/}
                            {/*        >*/}

                            {/*        </div>*/}
                            {/*    )*/}
                            {/*})}*/}

                            <div
                                className="container-timeline-div-div"
                                style={{
                                    width: `${tl}px`,
                                    left: `${ts}px`,
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
