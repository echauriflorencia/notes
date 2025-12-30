import { useNotes } from "../hooks/useNotes";
import { NotesList } from "../components/NotesList";

export function NotesPage() {
  const { notes, loading, error } = useNotes();

  if (loading) return <p>Cargando...</p>;
  if (error) return <p>Error: {error}</p>;

  return <NotesList notes={notes} />;
}
