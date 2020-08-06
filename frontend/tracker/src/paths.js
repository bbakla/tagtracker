export const basePathForProjects = "/projects"
export const createTagPath = basePathForProjects + "/{identifier}/tags";
export const deleteTagPath = createTagPath + "/{tagName}?deleteRemoteTag={isRemoteTagDeleted}";


