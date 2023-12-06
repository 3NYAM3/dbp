import React from 'react';

// test dataSet
const taskData = [
    {content: '작업 1', start: '2023-01-01', end: '2023-01-01'},
    {content: '작업 2', start: '2023-01-02', end: '2023-01-03'},
    {content: '작업 3', start: '2023-01-03', end: '2023-01-04'},
    {content: '작업 4', start: '2023-01-05', end: '2023-01-06'},
    {content: '작업 5', start: '2023-01-01', end: '2023-01-31'},
    {content: '작업 6', start: '2023-02-01', end: '2023-03-31'},
    {content: '작업 7', start: '2023-02-01', end: '2023-04-30'},
    {content: '작업 8', start: '2023-05-01', end: '2023-12-31'},
    {content: '작업 9', start: '2023-12-01', end: '2023-12-31'},
];
const projectData = {
    start: '2023-01-01',
    end: '2023-12-31'
}

const oneDayTo10px = (input) => { // 1일당 10px
    return (
        (new Date(input) / (1000 * 60 * 60 * 24)) * 10
    )
}

const ps = oneDayTo10px(projectData.start); // 프로젝트 시작
const pe = oneDayTo10px(projectData.end); // 프로젝트 끝
const pl = pe - ps; // 프로젝트 길이


function ProjectTimelinePage() {
    return (
        <>
            <div className="project-search">
                <h2>프로젝트명</h2>
                <p>23.01.01 ~ 23.12.31</p>
            </div>
            <div className="container-timeline">
                {taskData.map((task, index) => {

                    const ts = oneDayTo10px(task.start) - ps; // 작업 시작
                    const tl = oneDayTo10px(task.end) - oneDayTo10px(task.start); // 작업 길이

                    return (
                        <div
                            key={index}
                            style={{
                                width: `${pl}px`,

                            }}>
                            <div
                                style={{
                                    width: `${tl}px`, // 단위 px 추가
                                    left: `${ts}px`, // 단위 px 추가
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
