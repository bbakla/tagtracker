export const basePathForProjects = "/projects"
export const tagBasePath = basePathForProjects + "/{identifier}/tags";
export const tagWithIdPath = tagBasePath + "/{tagName}";
export const dependentOnPath = tagBasePath + "/{tagName}/dependent-on"
export const runJobPath = tagWithIdPath + "/jobs/{jobId}";




