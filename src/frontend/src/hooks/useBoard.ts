import {
  getBoardData,
  getBoardDetailData,
  postBoard,
  postBoardCommemt,
  postBoardCommentLike,
  searchBoard,
} from "@/apis/board";
import { boardRequest } from "@/types/board";
import { useMutation, useQuery } from "@tanstack/react-query";
import { useNavigate } from "react-router-dom";

export const useBoardGet = (boardName: string) => {
  return useQuery({
    queryKey: ["board", boardName],
    queryFn: () => getBoardData(boardName),
  });
};

export const useBoardDetailGet = ({
  boardName,
  postId,
}: {
  boardName: string;
  postId: number;
}) => {
  return useQuery({
    queryKey: ["board", boardName, "postId", postId],
    queryFn: () => getBoardDetailData({ boardName, postId }),
  });
};

export const useBoardPost = (data: boardRequest, images: File[]) => {
  const navigate = useNavigate();
  return useMutation({
    mutationFn: () => postBoard(data, images),
    onSuccess: () => {
      console.log("커뮤니티 글 등록 성공");
      navigate("/community");
    },
    onError: () => {
      console.log("커뮤니티 글 등록 실패");
    },
  });
};

export const useBoardSearch = (boardName: string, keyword: string) => {
  return useMutation({
    mutationFn: () => searchBoard(boardName, keyword),
    onSuccess: () => {
      console.log("커뮤니티 검색 성공");
    },
    onError: () => {
      console.log("커뮤니티 검색 실패");
    },
  });
};

export const useBoardCommentLike = (
  boardName: string,
  postId: number,
  commentId: number
) => {
  return useMutation({
    mutationFn: () => postBoardCommentLike(boardName, postId, commentId),
    onSuccess: () => {
      console.log("커뮤니티 좋아요 성공");
    },
    onError: () => {
      console.log("커뮤니티 좋아요 실패");
    },
  });
};

export const useBoardCommentPost = (
  boardName: string,
  postId: number,
  content: string,
  parentCommentId: number | null
) => {
  return useMutation({
    mutationFn: () =>
      postBoardCommemt(boardName, postId, content, parentCommentId),
    onSuccess: () => {
      console.log("커뮤니티 댓글 작성 성공");
      window.location.reload();
    },
    onError: () => {
      console.log("커뮤니티 댓글 작성 실패");
    },
  });
};
