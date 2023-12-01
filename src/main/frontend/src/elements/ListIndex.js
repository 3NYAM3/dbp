import propTypes from "prop-types";

const ListIndex = ({isProject, isSummary, isTask, project, summary, task, onClick}) => {

    return (
        <div
            className="project-container"
            onClick={onClick}
        >
            {isProject &&
                <>
                    <div>{project.title}</div>
                    <div>{project.kind}</div>
                    <div>{project.leader}</div>
                </>
            }
            {isSummary &&
                <>
                    <div>{summary.name}</div>
                    <div>{summary.content}</div>
                    <div>{summary.date}</div>
                </>
            }
            {isTask &&
                <>
                    <div>{task.title}</div>
                    <div>{task.name}</div>
                    <div>{task.start}</div>
                    <div>{task.end}</div>
                </>
            }

        </div>
    )
}

ListIndex.propTypes = {
    isProject: propTypes.bool,
    isSummary: propTypes.bool,
    isTask: propTypes.bool,
    project: propTypes.object,
    summary: propTypes.object,
    task: propTypes.object,
}
ListIndex.defaultProps = {
    isProject: false,
    isSummary: false,
    isTask: false,
}

export default ListIndex;