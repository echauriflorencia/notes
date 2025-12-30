import { Note } from "../types/note";

type Props = {
  notes: Note[];
};

export function NotesList({ notes }: Props) {
  if (notes.length === 0) {
    return <p>No hay notas</p>;
  }

  return (
    <ul>
      {notes.map((note) => (
        <li key={note.id}>
          <h3>{note.title}</h3>
          <p>{note.content}</p>
        </li>
      ))}
    </ul>
  );
}
