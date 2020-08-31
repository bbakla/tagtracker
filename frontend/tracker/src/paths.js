export const basePathForProjects = "/projects"
export const projectWithId = basePathForProjects + "/{identifier}"
export const tagBasePath = projectWithId + "/tags";
export const tagWithIdPath = tagBasePath + "/{tagName}";
export const dependentOnPath = tagBasePath + "/{tagName}/dependent-on"
export const runJobPath = tagWithIdPath + "/jobs/{jobId}";




