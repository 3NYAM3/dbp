import {useState} from "react";
import propTypes from "prop-types";
import {useNavigate} from "react-router-dom";
import axios from "axios";

const NoticeForm = ({editing}) => {
    const [content, setContent] = useState('');
    const navigate = useNavigate();
    const [title, setTitle] = useState('')

    return (
        <div className="container-common">
            <div>
                <h1>글 {editing ? '수정' : '쓰기'}</h1>
                <input
                    placeholder="제목"
                    value={title}
                    onChange={(e) => {
                        setTitle(e.target.value)
                    }}
                /><br/><br/>
                <textarea
                    placeholder="내용"
                    value={content}
                    onChange={(e) => {
                        setContent(e.target.value);
                    }}
                    rows="20"
                />
                <br/><br/>
                <button className="ok-common" onClick={() => {
                    axios.post(`/api/project/dashboard/${localStorage.getItem('projectNum')}/create`, {
                        title,
                        content
                    }, {headers: {'Authorization': `Bearer ${localStorage.getItem('isLoggedIn')}`}}).then((res) => {
                        console.log(res);
                    }).catch(e => {
                        console.log('글쓰기 post error')
                    })
                }}>{editing ? '수정' : '등록'}</button>
                <br/><br/>
                <button className="cancel-common" onClick={() => navigate('/project/dashboard')}>취소</button>
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