export const basePathForProjects = "/projects"
export const tagBasePath = basePathForProjects + "/{identifier}/tags";
export const deleteTagPath = tagBasePath + "/{tagName}?deleteRemoteTag={isRemoteTagDeleted}";
export const addDependentOnMe = tagBasePath + "/{tagName}/dependent-on-me"
export const addDependentOn = tagBasePath + "/{tagName}/dependent-on"


