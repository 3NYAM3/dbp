import HomePage from "./Pages/HomePage";
import LoginPage from "./Pages/LoginPage";
import CreateProjectPage from "./Pages/CreateProjectPage";
import ProjectSummaryPage from "./Pages/ProjectSummaryPage";
import ProjectTaskPage from "./Pages/ProjectTaskPage";
import ProjectTimelinePage from "./Pages/ProjectTimelinePage";
import ProjectManagementPage from "./Pages/ProjectManagementPage";
import CreateNoticePage from "./Pages/CreateNoticePage";
import CreateTaskPage from "./Pages/CreateTaskPage";

const routes = [

    {
        path: '/',
        element: <HomePage/>,
        auth: true
    },
    {
        path: '/login',
        element: <LoginPage/>
    },
    {
        path: '/create/project',
        element: <CreateProjectPage/>
    },
    {
        path: '/create/notice',
        element: <CreateNoticePage/>
    },
    {
        path: '/create/task',
        element: <CreateTaskPage/>
    },
    {
        path: '/project/summary',
        element: <ProjectSummaryPage/>
    },
    {
        path: '/project/task',
        element: <ProjectTaskPage/>
    },
    {
        path: '/project/timeline',
        element: <ProjectTimelinePage/>
    },
    {
        path: '/project/management',
        element: <ProjectManagementPage/>
    },
];

export default routes;