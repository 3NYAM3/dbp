import {useNavigate} from "react-router-dom";
import List from "../elements/List";

const HomePage = () => {
    const navigate = useNavigate();
    return (
        <div>
            <List isProject={true}/>
            <div
                className="project-add"
                onClick={() => navigate('/create/project')}
            >
                +
            </div>
        </div>
    );
};
export default HomePage