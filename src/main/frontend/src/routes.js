import Project from "./Pages/Project";
import LoginPage from "./Pages/LoginPage";
import CreateProjectPage from "./Pages/CreateProjectPage";
import ProjectSummaryPage from "./Pages/ProjectSummaryPage";
import ProjectTaskPage from "./Pages/ProjectTaskPage";
import ProjectTimelinePage from "./Pages/ProjectTimelinePage";
import ProjectManagementPage from "./Pages/ProjectManagementPage";
import CreateNoticePage from "./Pages/CreateNoticePage";
import CreateTaskPage from "./Pages/CreateTaskPage";
import ErrorPage from "./Pages/ErrorPage";
import ProtectedRoute from "./ProtectedRoute";

const routes = [
    {
        auth: true,
        path: '/',
        element: <ProtectedRoute/>,
    },
    {
        auth: true,
        path: '/project',
        element: <Project/>,
    },
    {
        path: '/login',
        element: <LoginPage/>
    },
    {
        auth: true,
        path: '/create/project',
        element: <CreateProjectPage/>
    },
    {
        auth: true,
        path: '/create/notice',
        element: <CreateNoticePage/>
    },
    {
        auth: true,
        path: '/create/task',
        element: <CreateTaskPage/>
    },
    {
        auth: true,
        path: '/project/summary',
        element: <ProjectSummaryPage/>
    },
    {
        auth: true,
        path: '/project/task',
        element: <ProjectTaskPage/>
    },
    {
        auth: true,
        path: '/project/timeline',
        element: <ProjectTimelinePage/>
    },
    {
        auth: true,
        path: '/project/management',
        element: <ProjectManagementPage/>
    },
    {
        path: '*',
        element: <ErrorPage/>
    },
];

export default routes;