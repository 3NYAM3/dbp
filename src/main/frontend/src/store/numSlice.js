import {createSlice} from "@reduxjs/toolkit";

const initialState = {
    projectNum: 0
}

const numSlice = createSlice({ //createSlice를 통해 state를 정의
    name: 'num',
    initialState,
    reducers: { // state를 변경하는 함수가 들어감
        setProjectNum:(state, action) =>{
            state.projectNum = action.payload;
            localStorage.setItem('projectNum', action.payload);
        },
    }
})

export const {setProjectNum} = numSlice.actions;

export default numSlice.reducer;