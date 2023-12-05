import {useNavigate} from "react-router-dom";
import List from "../elements/List";

const ProjectDashboardPage = () => {
    const navigate = useNavigate();
    return (
        <div>
            <List isDashboard={true}/>
            <div
                className="project-add"
                onClick={() => navigate('/create/notice')}
            >
                +
            </div>
        </div>


    )
}

export default ProjectDashboardPage;