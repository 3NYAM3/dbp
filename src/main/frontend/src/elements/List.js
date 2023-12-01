import {useState} from "react";
import { useNavigate} from "react-router-dom";
import ListIndex from "./ListIndex";
import propTypes from "prop-types";

const List = ({isProject, isSummary, isTask}) => {
    const navigate = useNavigate();
    const [searchText, setSearchText] = useState('');
    const onSearch = () => {
        // 검색 로직 구현
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
            {isSummary &&
                <div className="project-search">
                    <h2>프로젝트명</h2>
                    <p>23.11.30 ~ 23.12.31</p>
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
                    : isSummary ?
                        <>
                            <div>작성자</div>
                            <div>내용</div>
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
            {isProject ?
                <ListIndex
                    isProject={true}
                    project={{
                        title: "제목",
                        kind: "유형",
                        leader: "리더"
                    }}
                    onClick={() => navigate('/project/summary')}
                />
                : isSummary ?
                    <ListIndex
                        isSummary={true}
                        summary={{
                            name: "yp",
                            content: "내용",
                            date: "2019"
                        }}
                        // onClick={() => navigate('/project/summary')}
                    />
                    : isTask ?
                        <ListIndex
                            isTask={true}
                            task={{
                                title:"응애",
                                name:"정운",
                                start:"231101",
                                end:"231231"
                            }}
                            // onClick={() => navigate('/project/summary')}
                        />
                        : ""
            }

        </div>
    )
}
List.propTypes = {
    isProject: propTypes.bool,
    isSummary: propTypes.bool,
    isTask: propTypes.bool,
}
List.defaultProps = {
    isProject: false,
    isSummary: false,
    isTask: false,
}

export default List;