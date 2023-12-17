import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const PostPage = () => {
    const [title, setTitle] = useState('게시물 제목');
    const [author, setAuthor] = useState('작성자 이름');
    const [date, setDate] = useState('2022-01-01');
    const [content, setContent] = useState('게시물 내용');
    const [comments, setComments] = useState([
        { content: '초기 댓글 1', author: '작성자1', date: '2022-12-18 12:00' },
        { content: '초기 댓글 2', author: '작성자2', date: '2022-12-18 13:00' },
    ]);
    const [newComment, setNewComment] = useState('');
    const navigate = useNavigate();

    // 현재 날짜와 시간을 반환하는 함수
    const getCurrentDateTime = () => {
        const now = new Date();
        return now.toISOString().split('T')[0] + ' ' + now.toTimeString().split(' ')[0];
    };

    const addComment = () => {
        if (newComment.trim() !== '') {
            setComments([...comments, { content: newComment, author: '새 작성자', date: getCurrentDateTime() }]);
            setNewComment('');
            window.scrollTo({
                top: document.body.scrollHeight,
                behavior: 'smooth',
            });
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
        },
    };

    return (
        <div style={styles.container}>
            <div style={styles.header}>
                <h1 style={styles.title}>{title}</h1>
                <div style={styles.authorDate}>
                    {author} / {date}
                </div>
            </div>
            <p style={styles.content}>{content}</p>
            <div style={styles.commentSection}>
                <h3>댓글</h3>
                {comments.map((comment, index) => (
                    <div key={index} style={styles.comment}>
                        <strong>{comment.author}</strong>: <span style={styles.commentContent}>{comment.content}</span>
                        <span style={styles.commentDate}>{comment.date}</span>
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
                    <button style={styles.backButton} onClick={() => navigate(-1)}>목록</button>
                </div>
            </div>
        </div>
    );
};

export default PostPage;
