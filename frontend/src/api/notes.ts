import { apiFetch } from "./http";
import { Note } from "../types/note";

export function getNotes(): Promise<Note[]> {
  return apiFetch<Note[]>("/notes");
}
