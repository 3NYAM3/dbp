import {useEffect, useState} from "react";
import propTypes from "prop-types";
import {useNavigate} from "react-router-dom";
import axios from "axios";

const NoticeForm = ({editing}) => {
    const [content, setContent] = useState('');
    const navigate = useNavigate();
    const [title, setTitle] = useState('')

    useEffect(() => {
        if (editing) {
            axios.get(`/api/project/dashboard/notice/${localStorage.getItem('noticeNum')}`).then((res) => {
                console.log(res);
                setTitle(res.data.data.title);
                setContent(res.data.data.content);
            }).catch(e => {
                console.log('글 정보 가져오지 못함')
            })
        }
    }, []);

    const handleSubmit = (e) => {
        e.preventDefault();
        if (editing) { //
            // axios.put(`/api/project/dashboard/${localStorage.getItem('noticeNum')}`,
            //     {},
            //     {headers: {'Authorization': `Bearer ${localStorage.getItem('isLoggedIn')}`}})
        } else {
            axios.post(`/api/project/dashboard/${localStorage.getItem('projectNum')}/create`, {
                title,
                content
            }, {headers: {'Authorization': `Bearer ${localStorage.getItem('isLoggedIn')}`}}).then((res) => {
                navigate('/project/dashboard');
            }).catch(e => {
                console.log('글쓰기 post error')
            })

        }
    }

    return (
        <div className="container-common">
            <div>
                <form onSubmit={handleSubmit}>
                    <h1>글 {editing ? '수정' : '쓰기'}</h1>
                    <input
                        required
                        placeholder="제목"
                        value={title}
                        onChange={(e) => {
                            setTitle(e.target.value)
                        }}
                    /><br/><br/>
                    <textarea
                        required
                        placeholder="내용"
                        value={content}
                        onChange={(e) => {
                            setContent(e.target.value);
                        }}
                        rows="20"
                    />
                    <br/><br/>
                    <button type="submit" className="ok-common">{editing ? '수정' : '등록'}</button>
                    <br/><br/>
                    <button type="button" className="cancel-common"
                            onClick={() => {
                                editing ? navigate('/project/dashboard/post') :
                                    navigate('/project/dashboard')
                            }}>취소
                    </button>
                </form>
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