import {useNavigate} from "react-router-dom";

const ProjectManagementPage = () => {
    const navigate = useNavigate();
    const onSubmit = () =>{
        //구현
        navigate('/project/summary')
    }
    return (
        <div>
            <form>
                <div className="create-container">
                    <div>
                        <label>이름</label>
                        <input type="text" placeholder="이름" required/>
                        <br/><br/><br/>
                        <label>타입</label>
                        <input type="text" placeholder="타입" required/>
                        <br/><br/><br/>
                        <label>시작일</label>
                        <input type="date" placeholder="시작일" required/>
                        <br/><br/><br/>
                        <label>마감일</label>
                        <input type="date" placeholder="마감일" required/>
                        <br/><br/><br/>

                        <div>회원명단</div>

                        <br/><br/>
                        <button
                            type="submit"
                            onClick={onSubmit}
                        >수정
                        </button>
                        <br/><br/>
                        <input
                            className="btn-cancel"
                            type="button"
                            value="취소"
                            onClick={()=>navigate('/project/summary')}
                        />
                    </div>
                </div>
            </form>
        </div>
    )
}

export default ProjectManagementPage;