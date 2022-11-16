export interface Commentt {
    commentID: number;
    studentID: number;
    subjectID: number;
    universityID: number;
    rating: number;
    description: string;
    upvote: number;
    dateCreated: Date;
    dateEdited: Date;
    upvoted: boolean;
    reported: boolean;
    universityName: string;
    subjectName: string;
}
