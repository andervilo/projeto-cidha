import { IConcessaoLiminar } from 'app/shared/model/concessao-liminar.model';
import { IComarca } from 'app/shared/model/comarca.model';
import { IQuilombo } from 'app/shared/model/quilombo.model';

export interface IProcesso {
  id?: number;
  oficio?: string;
  assunto?: any;
  linkUnico?: string;
  linkTrf?: string;
  subsecaoJudiciaria?: string;
  turmaTrf1?: string;
  numeroProcessoAdministrativo?: string;
  numeroProcessoJudicialPrimeiraInstancia?: string;
  numeroProcessoJudicialPrimeiraInstanciaLink?: string;
  numeroProcessoJudicialPrimeiraInstanciaObservacoes?: any;
  parecer?: boolean;
  concessaoLiminar?: IConcessaoLiminar;
  comarcas?: IComarca[];
  quilombos?: IQuilombo[];
}

export const defaultValue: Readonly<IProcesso> = {
  parecer: false,
};
