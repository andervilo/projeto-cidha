import { IProcesso } from 'app/shared/model/processo.model';

export interface IConcessaoLiminar {
  id?: number;
  descricao?: any;
  processos?: IProcesso[];
}

export const defaultValue: Readonly<IConcessaoLiminar> = {};
