import React from 'react';

const data = [
    {content: '작업 1', start: '2023-01-01', end: '2023-02-01'},
    {content: '작업 2', start: '2023-02-01', end: '2023-03-01'},
    {content: '작업 3', start: '2023-03-01', end: '2023-04-01'},
    {content: '작업 4', start: '2023-04-01', end: '2023-09-01'},
    {content: '작업 5', start: '2023-12-01', end: '2029-12-31'}
];

// 프로젝트 시작일
const ps = '2023-01-01';
// 프로젝트 마감일
const pe = '2023-12-31';
// 작업 시작일
// 작업 마감일

function ProjectTimelinePage() {
    return (
        <div className="container-timeline">
            {data.map((task, index) => {
                const ts = new Date(task.start);
                const te = new Date(task.end);
                const test = new Date(ps);

                const n = (ts - test) / (1000 * 60 * 60 * 24)
                const n1 = (te - ts) / (1000 * 60 * 60 * 24)

                // const startDate = new Date(task.start);
                // const endDate = new Date(task.end);
                // const daysFromStart = (startDate - new Date(data[0].start)) / (1000 * 60 * 60 * 24); // 날짜 간격을 일로 계산

                // const daysFromEnd = (endDate - new Date(data[0].end)) / (1000 * 60 * 60 * 24); // 날짜 간격을 일로 계산
                // const marginLeft = `${daysFromStart * 2}px`; // 간격을 CSS px로 변환
                // const size = `${daysFromStart * 2 - daysFromEnd * 2}px`;

                return (
                    <>
                        <div
                            key={index}
                            style={{
                                alignItems:"center",
                                fontSize:"10px",
                                minWidth:"100px",
                                width: n1,
                                marginLeft: n,
                                height: '30px',
                                overflow: "hidden",
                                cursor:"pointer",

                                // paddingRight: "0px"
                            }}
                        >
                            {task.content}
                        </div>
                        <hr/>
                    </>
                );
            })}
        </div>
    );
}

export default ProjectTimelinePage;
