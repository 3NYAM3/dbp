import {useNavigate} from "react-router-dom";

const CreateProjectPage = () => {
    const navigate = useNavigate();
    const onSubmit = () => {
        // 구현
        navigate('/');
    }
    return (
        <div>
            <form>
                <div className="create-container">
                    <div>
                        <h1>프로젝트 생성</h1><br/>
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
                        <br/><br/><br/><br/>
                        <button
                            type="submit"
                            onClick={onSubmit}>
                            만들기
                        </button>
                    </div>
                </div>
            </form>
        </div>
    )
}

export default CreateProjectPage;