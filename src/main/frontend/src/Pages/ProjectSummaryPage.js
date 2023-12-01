import {useNavigate} from "react-router-dom";
import List from "../elements/List";

const ProjectSummaryPage = () => {
    const navigate = useNavigate();
    return (
        <div>
            <List isSummary={true}/>
            <div
                className="project-add"
                onClick={() => navigate('/create/notice')}
            >
                +
            </div>
        </div>


    )
}

export default ProjectSummaryPage;