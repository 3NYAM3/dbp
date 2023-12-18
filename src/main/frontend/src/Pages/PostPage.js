import React, {useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import axios from "axios";
import {useSelector} from "react-redux";

const PostPage = () => {
    const [title, setTitle] = useState('');
    const [writer, setWriter] = useState('');
    const [date, setDate] = useState('');
    const [content, setContent] = useState('');
    const [comments, setComments] = useState([]);
    const [newComment, setNewComment] = useState('');
    const navigate = useNavigate();
    const [writerEmail, setWriterEmail] = useState('');
    const [email, setEmail] = useState('');

    const addComment = () => {
        axios.post(`/api/project/dashboard/review/${localStorage.getItem('noticeNum')}`, {content: newComment}, {headers: {'Authorization': `Bearer ${localStorage.getItem('isLoggedIn')}`}}).then((res) => {
            setComments([...comments, {
                content: res.data.data.content,
                writerName: res.data.data.writerName,
                writingTime: res.data.data.writingTime,
                reviewId: res.data.data.reviewId,
                writer: res.data.data.writer,
            }]);
            setNewComment('');
            window.scrollTo({
                top: document.body.scrollHeight,
                behavior: 'smooth',
            });
        }).catch(e => {
            console.log('댓글 작성 실패')
        })

    };
    useEffect(() => {
        // 글 제목, 작성자, 작성일, 내용
        axios.get(`/api/project/dashboard/notice/${localStorage.getItem('noticeNum')}`).then((res) => {
            setTitle(res.data.data.title);
            setWriter(res.data.data.writer);
            setDate(res.data.data.createTime);
            setContent(res.data.data.content);
            setWriterEmail(res.data.data.email);
        }).catch(e => {
            console.log('글 정보 받아오기 실패');
        })

        // 댓글 받아오기
        axios.get(`/api/project/dashboard/review/${localStorage.getItem('noticeNum')}`).then((res) => {
            setComments(res.data.data);
        }).catch(e => {
            console.log('댓글 받아오기 실패');
        })

        // 이메일 받아오기
        axios.get('/api/members/info', {headers: {'Authorization': `Bearer ${localStorage.getItem('isLoggedIn')}`}}).then((res) => { // get으로 가져옴
            setEmail(res.data.data.email);
        }).catch(e => { // 못가져 왔을 경우 예외처리
            console.log('이메일 받아오지 못함');
        })
    }, []);

    const styles = {
        container: {
            maxWidth: '768px',
            margin: '40px auto',
            padding: '20px',
            backgroundColor: '#f7f7f7',
            borderRadius: '8px',
            boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)'
        },
        header: {
            display: 'flex',
            justifyContent: 'space-between',
            alignItems: 'center',
            marginBottom: '20px',
        },
        title: {
            fontSize: '2rem',
            borderBottom: '1px solid #e1e1e1',
            paddingBottom: '10px',
        },
        authorDate: {
            fontStyle: 'italic',
            color: '#6c757d',
        },
        content: {
            fontSize: '1rem',
            marginBottom: '20px',
            wordWrap: 'break-word'
        },
        commentSection: {
            marginTop: '30px',
        },
        comment: {
            backgroundColor: '#fff',
            padding: '10px 15px',
            borderRadius: '4px',
            margin: '5px 0',
        },
        commentDate: {
            float: 'right',
            fontSize: '0.8rem',
            color: '#6c757d',
            fontStyle: 'italic',
        },
        commentContent: {},
        textarea: {
            boxSizing: 'border-box',
            width: '100%',
            padding: '15px',
            margin: '10px 0',
            borderRadius: '4px',
            border: '1px solid #ddd',
        },
        buttonContainer: {
            display: 'flex',
            justifyContent: 'space-between',
            marginTop: '20px',
        },
        button: {
            padding: '10px 20px',
            backgroundColor: '#0056b3',
            color: '#fff',
            border: 'none',
            borderRadius: '4px',
            cursor: 'pointer',
            fontSize: '1rem',
        },
        backButton: {
            padding: '10px 20px',
            backgroundColor: '#6c757d',
            color: '#fff',
            border: 'none',
            borderRadius: '4px',
            cursor: 'pointer',
            fontSize: '1rem',
        }, xButton: {
            float: 'right',
            color: '#FF8888',
            backgroundColor: 'transparent',
            border: 'none',
            marginRight: '5px',
            cursor: 'pointer',
        }, oButton: {
            float: 'right',
            color: '#8888FF',
            backgroundColor: 'transparent',
            border: 'none',
            marginRight: '5px',
            cursor: 'pointer',
        },
    };

    return (
        <div style={styles.container}>
            <div style={styles.header}>
                <h1 style={styles.title}>{title}</h1>
                <div style={styles.authorDate}>
                    <span style={styles.commentDate}>{writer} / {date}</span>
                    {writerEmail === email && <button style={styles.xButton} onClick={() => {
                        // 게시글 삭제
                        axios.delete(`/api/project/dashboard/${localStorage.getItem('noticeNum')}`, {headers: {'Authorization': `Bearer ${localStorage.getItem('isLoggedIn')}`}})
                            .then((res) => {
                                navigate('/project/dashboard');
                            }).catch(e => {
                            console.log('게시물 삭제 못함')
                        })
                    }}>지우기
                    </button>}
                    {writerEmail === email && <button style={styles.oButton} onClick={() => {
                        // todo 게시글 수정
                        navigate('/project/dashboard/edit')
                    }}>수정
                    </button>}
                </div>
            </div>
            <p style={styles.content}>{content}</p>
            <div style={styles.commentSection}>
                <h3>댓글</h3>
                {comments.map((comment) => (
                    <div key={comment.reviewId}
                         style={styles.comment}
                    >
                        <strong>{comment.writerName}</strong>: <span
                        style={styles.commentContent}>{comment.content}</span>
                        <span style={styles.commentDate}>{comment.writingTime}</span>
                        {(comment.writer === email) && <button
                            style={styles.xButton}
                            onClick={() => {
                                axios.delete(`/api/project/dashboard/review/${comment.reviewId}`,
                                    {headers: {'Authorization': `Bearer ${localStorage.getItem('isLoggedIn')}`}})
                                    .then((res) => {
                                        setComments(comments.filter((c) => c.reviewId !== comment.reviewId));
                                    }).catch(e => {
                                    console.log('댓글 삭제 실패')
                                })
                            }}>X</button>}
                    </div>
                ))}
                <textarea
                    style={styles.textarea}
                    value={newComment}
                    onChange={(e) => setNewComment(e.target.value)}
                    placeholder="댓글을 작성하세요."
                />
                <div style={styles.buttonContainer}>
                    <button style={styles.button} onClick={addComment}>댓글 달기</button>
                    <button style={styles.backButton} onClick={() => navigate('/project/dashboard')}>목록</button>
                </div>
            </div>
        </div>
    );
};

export default PostPage;
