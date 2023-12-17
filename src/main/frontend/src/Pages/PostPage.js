import React, {useState} from 'react';
import {useNavigate} from "react-router-dom";

const PostPage = () => {
    const [title, setTitle] = useState('게시물 제목');
    const [content, setContent] = useState('게시물 내용');
    const [comments, setComments] = useState(['초기 댓글 1', '초기 댓글 2']);
    const [newComment, setNewComment] = useState('');
    const navigate = useNavigate(); // useNavigate 훅을 사용합니다.

    const addComment = () => {
        if (newComment !== '') {
            setComments([...comments, newComment]);
            setNewComment('');
            window.scrollTo(0, document.body.scrollHeight);
        }
    };

    const styles = {
        container: {
            maxWidth: '768px',
            margin: '40px auto',
            padding: '20px',
            backgroundColor: '#f7f7f7',
            borderRadius: '8px',
            boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)'
        },
        title: {
            fontSize: '2rem',
            borderBottom: '1px solid #e1e1e1',
            paddingBottom: '10px',
            marginBottom: '20px'
        },
        content: {
            fontSize: '1rem',
            marginBottom: '20px'
        },
        commentSection: {
            marginTop: '30px'
        },
        comment: {
            backgroundColor: '#fff',
            padding: '10px 15px',
            borderRadius: '4px',
            margin: '5px 0'
        },
        textarea: {
            boxSizing: 'border-box',
            width: '100%',
            padding: '15px',
            margin: '10px 0',
            borderRadius: '4px',
            border: '1px solid #ddd'
        },
        buttonContainer: {
            display: 'flex',
            justifyContent: 'space-between', // 버튼을 양 끝으로 정렬합니다.
            marginTop: '20px' // 버튼과 텍스트 영역 사이의 간격을 조정합니다.
        },
        button: {
            padding: '10px 20px',
            backgroundColor: '#0056b3',
            color: '#fff',
            border: 'none',
            borderRadius: '4px',
            cursor: 'pointer',
            fontSize: '1rem'
        },
        backButton: {
            padding: '10px 20px',
            backgroundColor: '#6c757d',
            color: '#fff',
            border: 'none',
            borderRadius: '4px',
            cursor: 'pointer',
            fontSize: '1rem',
        },
    };

    return (
        <div style={styles.container}>
            <h1 style={styles.title}>{title}</h1>
            <p style={styles.content}>{content}</p>
            <div style={styles.commentSection}>
                <h3>댓글</h3>
                {comments.map((comment, index) => (
                    <div key={index} style={styles.comment}>{comment}</div>
                ))}
                <textarea
                    style={styles.textarea}
                    value={newComment}
                    onChange={(e) => setNewComment(e.target.value)}
                    placeholder="댓글을 작성하세요."
                />

                <div style={styles.buttonContainer}>
                    <button style={styles.button} onClick={addComment}>댓글 달기</button>
                    <button style={styles.backButton} onClick={() => navigate(-1)}>목록</button>
                </div>
            </div>
        </div>
    );
};

export default PostPage;
