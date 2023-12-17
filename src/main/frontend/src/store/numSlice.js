import {createSlice} from "@reduxjs/toolkit";

const tmp = () => {
    try {
        if (localStorage.getItem('projectNum')) {
            const a = localStorage.getItem('projectNum');
            return Number(a)
        } else return 0
    } catch {
        return 0
    }
}

const tmp1 = () => {
    try {
        if (localStorage.getItem('taskNum')) {
            const a = localStorage.getItem('taskNum');
            return Number(a)
        } else return 0
    } catch {
        return 0
    }
}


const initialState = {
    projectNum: tmp(),
    taskNum: tmp1()
}

const numSlice = createSlice({ //createSlice를 통해 state를 정의
    name: 'num',
    initialState,
    reducers: { // state를 변경하는 함수가 들어감
        setProjectNum: (state, action) => {
            state.projectNum = action.payload;
            localStorage.setItem('projectNum', action.payload);
        },
        setTaskNum: (state, action) => {
            state.projectNum = action.payload;
            localStorage.setItem('taskNum', action.payload);
        },
    }
})

export const {setProjectNum, setTaskNum} = numSlice.actions;

export default numSlice.reducer;