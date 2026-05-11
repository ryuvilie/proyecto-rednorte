import { Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import ProtectedRoute from "./components/ProtectedRoute";

import HomePage from "./pages/HomePage";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";

import PacientesPage from "./pages/PacientesPage";
import ListaEsperaPage from "./pages/ListaEsperaPage";
import CitasPage from "./pages/CitasPage";

import ReportesPage from "./pages/ReportesPage";

import RegistroPacientePage from "./pages/RegistroPacientePage";
import RegistroListaEsperaPage from "./pages/RegistroListaEsperaPage";
import RegistroCitaPage from "./pages/RegistroCitaPage";
import HorasDisponiblesPage from "./pages/HorasDisponiblesPage";
import DoctorDashboardPage from "./pages/DoctorDashboardPage";

function App() {
  return (
    <>
      <Navbar />

      <Routes>
        <Route path="/" element={<HomePage />} />

        <Route path="/login" element={<LoginPage />} />
        <Route path="/registro" element={<RegisterPage />} />

        <Route
          path="/pacientes"
          element={
            <ProtectedRoute requireAdmin>
              <PacientesPage />
            </ProtectedRoute>
          }
        />
      <Route path="/reportes" element={<ReportesPage />} />
        <Route
          path="/pacientes/registro"
          element={
            <ProtectedRoute requireAdmin>
              <RegistroPacientePage />
            </ProtectedRoute>
          }
        />

        <Route
          path="/lista-espera"
          element={
            <ProtectedRoute>
              <ListaEsperaPage />
            </ProtectedRoute>
          }
        />

        <Route
          path="/lista-espera/registro"
          element={
            <ProtectedRoute requireAdmin>
              <RegistroListaEsperaPage />
            </ProtectedRoute>
          }
        />

        <Route
          path="/citas"
          element={
            <ProtectedRoute>
              <CitasPage />
            </ProtectedRoute>
          }
        />

        <Route
          path="/citas/registro"
          element={
            <ProtectedRoute requireAdmin>
              <RegistroCitaPage />
            </ProtectedRoute>
          }
        />

        <Route
          path="/horas-disponibles"
          element={
            <ProtectedRoute requirePaciente>
              <HorasDisponiblesPage />
            </ProtectedRoute>
          }
        />

        <Route
          path="/doctor"
          element={
            <ProtectedRoute requireDoctor>
              <DoctorDashboardPage />
            </ProtectedRoute>
          }
        />
      </Routes>
    </>
  );
}

export default App;