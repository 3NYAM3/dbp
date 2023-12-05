import {useState} from "react";
import propTypes from "prop-types";
import {useNavigate} from "react-router-dom";

const NoticeForm = ({editing}) => {
    const [content, setContent] = useState('');
    const navigate = useNavigate();

    return (
        <div className="container-common">
            <div>
                <h1>공지사항 {editing?'수정':'등록'}</h1>
                <textarea
                    placeholder="공지사항 입력"
                    value={content}
                    onChange={(e) => {
                        setContent(e.target.value);
                    }}
                    rows="20"
                />
                <br/><br/>
                <button className="ok-common">{editing?'수정':'등록'}</button>
                <br/><br/>
                <button className="cancel-common" onClick={()=>navigate('/project/dashboard')}>취소</button>
            </div>
        </div>
    )
}

NoticeForm.propType = {
    editing: propTypes.bool,
}

NoticeForm.defaultProps = {
    editing: false,
}

export default NoticeForm;