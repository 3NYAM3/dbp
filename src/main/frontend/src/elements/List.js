import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import ListIndex from "./ListIndex";
import propTypes from "prop-types";
import axios from "axios";
import {useDispatch, useSelector} from "react-redux";
import {setProjectNum} from "../store/numSlice";

const List = ({isProject, isDashboard, isTask}) => {
    const navigate = useNavigate();
    const [searchText, setSearchText] = useState('');
    const [oriList, setOriList] = useState([]);
    const [dataList, setDataList] = useState([]);
    const dispatch = useDispatch();
    const num = useSelector(state => state.num.projectNum);
    const [projectName, setProjectName] = useState('');
    const [projectDate, setProjectDate] = useState('');


    useEffect(() => {
        if (isProject) {
            axios.get('/api/project/', {headers: {'Authorization': `Bearer ${localStorage.getItem('isLoggedIn')}`}})
                .then((res) => {
                    setDataList(res.data.data);
                })
                .catch(e => {
                    console.log('유저 정보 가져오지 못함');
                });
        } else if (isDashboard) {
            axios.get(`/api/project/dashboard/${localStorage.getItem('projectNum')}/`).then((res) => {
                setProjectName(res.data.data.projectName);
                setProjectDate(res.data.data.startDate+" ~ "+res.data.data.lastDate);
            }).catch(e => {
                console.log('대시보드 정보 가져오지 못함');
            });
        }
    }, []);

    const renderProject = () => {
        // setDataList(oriList);
        return dataList.map((data) => {
            return (
                <ListIndex
                    key={data.projectId}
                    isProject={true}
                    project={{
                        title: data.title,
                        kind: data.type,
                        leader: data.leaderEmail
                    }}
                    onClick={() => {
                        navigate(`/project/dashboard`)
                        dispatch(setProjectNum(data.projectId)); // 프로젝트 받아온 번호로 지정
                    }}
                />
            )
        })
    }

    const onSearch = () => {
        // if (isProject){
        //     console.log(dataList.filter(item => item.title.includes(searchText)))
        // }
        // console.log('test')
        // return(
        //     <div>dd</div>
        // )
    }
    return (
        <div>
            {(isProject || isTask) &&
                <div className="project-search">
                    <h2>{isProject ? "프로젝트" : isTask ? "작업" : ""}</h2>
                    <input
                        type='text'
                        placeholder="검색"
                        value={searchText}
                        onChange={(e) => setSearchText(e.target.value)}
                        onKeyUp={onSearch}
                    />
                </div>
            }
            {isDashboard &&
                <div className="project-search">
                    <h2>{projectName}</h2>
                    <p>{projectDate}</p>
                </div>
            }
            <br/>
            <div
                style={{
                    cursor: "default",
                    backgroundColor: "initial",
                    fontWeight: "bold"
                }}
                className="project-container"
            >
                {isProject ?
                    <>
                        <div>이름</div>
                        <div>유형</div>
                        <div>리더</div>
                    </>
                    : isDashboard ?
                        <>
                            <div>제목</div>
                            <div>작성자</div>
                            <div>작성일</div>
                        </>
                        : isTask ?
                            <>
                                <div>작업</div>
                                <div>담당자</div>
                                <div>시작일</div>
                                <div>마감일</div>
                            </>
                            : ""
                }
            </div>
            {isProject ? renderProject()
                : isDashboard ?
                    <ListIndex
                        isDashboard={true}
                        dashboard={{
                            name: "yp",
                            content: "내용",
                            date: "2019"
                        }}
                        // onClick={() => navigate('/project/dashboard')}
                        // onClick={() => navigate(`/blogs/${post.id}`)} routes에 추가
                    />
                    : isTask ?
                        <ListIndex
                            isTask={true}
                            task={{
                                title: "응애",
                                name: "정운",
                                start: "231101",
                                end: "231231"
                            }}
                            // onClick={() => navigate('/project/dashboard')}
                        />
                        : ""
            }

        </div>
    )
}
List.propTypes = {
    isProject: propTypes.bool,
    isDashboard: propTypes.bool,
    isTask: propTypes.bool,
}
List.defaultProps = {
    isProject: false,
    isDashboard: false,
    isTask: false,
}

export default List;